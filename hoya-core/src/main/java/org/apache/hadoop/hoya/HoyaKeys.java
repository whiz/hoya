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

package org.apache.hadoop.hoya;


import org.apache.hadoop.hoya.api.OptionKeys;

/**
 * Keys and various constants for Hoya
 */
public interface HoyaKeys {

  String ROLE_MASTER = "master";
  
  /**
   * The path under which cluster and temp data are stored
   * {@value}
   */
  String HOYA_BASE_DIRECTORY = ".hoya";

  /**
   *  name of the relative path to install hBase into:  {@value}
   */
  String LOCAL_TARBALL_INSTALL_SUBDIR = "local";


  /**
   * Application type for YARN  {@value}
   */
  String APP_TYPE = "hoya";

  /**
   * JVM arg to force IPv4  {@value}
   */
  String JAVA_FORCE_IPV4 = "-Djava.net.preferIPv4Stack=true";

  /**
   * JVM arg to go headless  {@value}
   */

  String JAVA_HEADLESS = "-Djava.awt.headless=true";
  String FORMAT_D_CLUSTER_NAME = "-Dhoya.cluster.name=%s";
  String FORMAT_D_CLUSTER_TYPE = "-Dhoya.app.type=%s";


  /**
   * This is the name of the dir/subdir containing
   * the hbase conf that is propagated via YARN
   *  {@value}
   */
  String PROPAGATED_CONF_DIR_NAME = "conf";
  String GENERATED_CONF_DIR_NAME = "generated";
  String ORIG_CONF_DIR_NAME = "original";
  String DATA_DIR_NAME = "database";
  String CLUSTER_SPECIFICATION_FILE = "cluster.json";

  int MIN_HEAP_SIZE = 0;

  /**
   * XML resource listing the standard Hoya providers
   * {@value}
   */
  String HOYA_XML ="org/apache/hadoop/hoya/hoya.xml";

  /**
   * pattern to identify a hoya provider
   * {@value}
   */
  String HOYA_PROVIDER_KEY = "hoya.provider.%s";

  /**
   * conf option set to point to where the config came from
   * {@value}
   */
  String KEY_HOYA_TEMPLATE_ORIGIN = "hoya.template.origin";

  String CLUSTER_DIRECTORY = "cluster";
  /**
   * Command to issue to override any specific role in the in-AM master
   * script. Used for things like issuing a version command in testing
   */
  String OPTION_HOYA_MASTER_COMMAND =
    "hoya.test.master.command";


  /**
   * delay for container startup
   */
  int CONTAINER_STARTUP_DELAY = 5000;

  /**
   * Original name for the default FS. This is still 
   * expected by applications deployed
   */
  String FS_DEFAULT_NAME_CLASSIC = "fs.default.name";

  /**
   * JVM property to define the hoya configuration directory;
   * this is set by the hoya script: {@value}
   */
  String PROPERTY_HOYA_CONF_DIR = "hoya.confdir";

  /**
   * name of generated dir for this conf: {@value}
   */
  String SUBMITTED_HOYA_CONF_DIR = "hoya_confdir";

  /**
   * name of the Hoya client resource
   * loaded when the service is loaded.
   */
  String HOYA_CLIENT_RESOURCE = "hoya-client.xml";

  /**
   * The name of the resource to put on the classpath
   * This only goes up on a real cluster, not a test run.
   */
  String HOYA_SERVER_RESOURCE = "hoya-server.xml";

  String PROPERTY_HOYA_RESOURCE_ORIGIN = "hoya.client.resource.origin";

  /**
   * Hoya principal
   */
  String KEY_HOYA_PRINCIPAL = "hoya.kerberos.principal";

  /**
   * Name of the property for ACLs for Hoya AM.
   * {@value}
   */
  String KEY_HOYA_ACL = "security.hoya.protocol.acl";
}
