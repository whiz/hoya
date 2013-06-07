/*
 * Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */





package org.apache.hadoop.hoya.yarn.cluster

import groovy.util.logging.Commons
import org.apache.hadoop.hoya.yarn.CommonArgs
import org.apache.hadoop.hoya.yarn.client.ClientArgs
import org.apache.hadoop.hoya.yarn.client.HoyaClient
import org.apache.hadoop.yarn.conf.YarnConfiguration
import org.apache.hadoop.yarn.service.launcher.ServiceLauncher
import org.junit.Before
import org.junit.Test

/**
 * Test of RM creation. This is so the later test's prereq's can be met
 */
@Commons
class TestActionList extends YarnMiniClusterTestBase {

  @Before
  public void setup() {
    createCluster("TestActionList", new YarnConfiguration(), 1, false)
  }
  
  @Test
  public void testListThisUserNoClusters() throws Throwable {
    log.info("RM address = ${RMAddr}")
    ServiceLauncher launcher = launchHoyaClientAgainstMiniMR(
        //config includes RM binding info
        new YarnConfiguration(miniCluster.config),
        //varargs list of command line params
        [ClientArgs.ACTION_LIST,
        ClientArgs.ARG_MANAGER, RMAddr]
    )
    assert launcher.serviceExitCode == 0
    HoyaClient hoyaClient = (HoyaClient) launcher.service
  }
  
  @Test
  public void testListAllUsersNoClusters() throws Throwable {
    log.info("RM address = ${RMAddr}")
    ServiceLauncher launcher = launchHoyaClientAgainstMiniMR(
        //config includes RM binding info
        new YarnConfiguration(miniCluster.config),
        //varargs list of command line params
        [ClientArgs.ACTION_LIST,
        ClientArgs.ARG_MANAGER, RMAddr,
        ClientArgs.ARG_USER,""]
    )
    assert launcher.serviceExitCode == 0
  }

  @Test
  public void testListLiveCluster() throws Throwable {
    //launch fake master
    String name = "testListLiveCluster"
    
    //launch the cluster
    ServiceLauncher launcher = createMasterlessAM(name, 0)
    
    //now list
    launcher = launchHoyaClientAgainstMiniMR(
        //config includes RM binding info
        new YarnConfiguration(miniCluster.config),
        //varargs list of command line params
        [ClientArgs.ACTION_LIST,
        ClientArgs.ARG_MANAGER, RMAddr,
        CommonArgs.ARG_USER, USERNAME]
        
    )
    HoyaClient hoyaClient = (HoyaClient) launcher.service

  }


}