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
package org.apache.beam.sdk.io.solace.crosslanguage;

public class SolaceIOConfiguration {
  private String queueName = "";
  private String sempHost = "";
  private String sempUsername = "";
  private String sempPassword = "";
  private String vpnName = "";
  private String host = "";
  private String username = "";
  private String password = "";

  public String getQueueName() {
    return queueName;
  }

  public void setQueueName(String queueName) {
    this.queueName = queueName;
  }

  public String getSempHost() {
    return sempHost;
  }

  public void setSempHost(String sempHost) {
    this.sempHost = sempHost;
  }

  public String getSempUsername() {
    return sempUsername;
  }

  public void setSempUsername(String sempUsername) {
    this.sempUsername = sempUsername;
  }

  public String getSempPassword() {
    return sempPassword;
  }

  public void setSempPassword(String sempPassword) {
    this.sempPassword = sempPassword;
  }

  public String getVpnName() {
    return vpnName;
  }

  public void setVpnName(String vpnName) {
    this.vpnName = vpnName;
  }

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
