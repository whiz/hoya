/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.hadoop.hoya.providers;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hoya.api.ClusterDescription;
import org.apache.hadoop.hoya.api.ClusterNode;
import org.apache.hadoop.hoya.tools.HoyaUtils;
import org.apache.hadoop.hoya.yarn.service.ForkedProcessService;
import org.apache.hadoop.hoya.yarn.service.SequenceService;
import org.apache.hadoop.service.Service;
import org.apache.hadoop.yarn.service.launcher.ExitCodeProvider;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractProviderService extends SequenceService implements
                                                                      ProviderService {

  public AbstractProviderService(String name) {
    super(name);
  }

  protected String cmd(Object... args) {
    List<String> list = new ArrayList<String>(args.length);
    for (Object arg : args) {
      list.add(arg.toString());
    }
    return HoyaUtils.join(list, " ");
  }

  @Override
  public Configuration getConf() {
    return getConfig();
  }

  @Override // ExitCodeProvider
  public int getExitCode() {
    Service prev = getPreviousService();
    if (prev != null && prev instanceof ExitCodeProvider) {
      return ((ExitCodeProvider) prev).getExitCode();
    } else {
      return 0;
    }
  }
  
  protected ForkedProcessService latestProcess() {
    Service current = getCurrentService();
    Service prev = getPreviousService();
    
    Service latest = current!=null? current:prev;
    if (latest instanceof ForkedProcessService) {
      return (ForkedProcessService) latest;
    } else {
      return null;
    }
  }

  /**
   * Build a status report 
   * @param masterNode node to fill in
   * @return true iff there was a process to build a status
   * report from
   */
  @Override
  public boolean buildStatusReport(ClusterNode masterNode) {
    ForkedProcessService masterProcess = latestProcess ();
    if (masterProcess != null) {
      masterNode.command = masterProcess.getCommandLine();
      masterNode.state = masterProcess.isProcessStarted() ?
                         ClusterDescription.STATE_LIVE :
                         ClusterDescription.STATE_STOPPED;

      masterNode.diagnostics = "Exit code = " + masterProcess.getExitCode();
      //pull in recent lines of output
      List<String> output = masterProcess.getRecentOutput();
      masterNode.output = output.toArray(new String[output.size()]);
      return true;
    } else {
      masterNode.state = ClusterDescription.STATE_CREATED;
      masterNode.output = new String[0];
      return false;
    }

  }
}