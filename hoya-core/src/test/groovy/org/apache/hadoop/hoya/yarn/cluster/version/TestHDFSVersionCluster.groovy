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



package org.apache.hadoop.hoya.yarn.cluster.version

import groovy.util.logging.Slf4j
import org.apache.hadoop.hoya.api.ClusterDescription
import org.apache.hadoop.hoya.yarn.client.HoyaClient
import org.apache.hadoop.hoya.yarn.providers.hbase.HBaseMiniClusterTestBase
import org.apache.hadoop.yarn.service.launcher.ServiceLauncher
import org.junit.Test

/**
 * Test of RM creation. This is so the later test's prereq's can be met
 */
@Slf4j
class TestHDFSVersionCluster extends HBaseMiniClusterTestBase {

  @Test
  public void testHDFSVersionCluster() throws Throwable {
    describe "create a cluster, exec the version command"

    String clustername = "TestHDFSVersionCluster"
    createMiniCluster(clustername, createConfiguration(), 1, 1, 1, true, true)
    ServiceLauncher launcher = createHBaseCluster(clustername,
                                                 0,
                                                 HBASE_VERSION_COMMAND_SEQUENCE,
                                                 true,
                                                 true)
    assert launcher.serviceExitCode == 0
    HoyaClient hoyaClient = (HoyaClient) launcher.service
    ClusterDescription status = hoyaClient.getClusterStatus(clustername)
    log.info("Status $status")
    waitForAppToFinish(hoyaClient)
  }

}
