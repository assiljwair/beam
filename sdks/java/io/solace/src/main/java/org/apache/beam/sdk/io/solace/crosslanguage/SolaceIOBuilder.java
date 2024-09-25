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

import com.solacesystems.jcsmp.BytesXMLMessage;
import org.apache.beam.sdk.io.solace.SolaceIO;
import org.apache.beam.sdk.io.solace.broker.BasicAuthJcsmpSessionServiceFactory;
import org.apache.beam.sdk.io.solace.broker.BasicAuthSempClientFactory;
import org.apache.beam.sdk.io.solace.data.Solace.Queue;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.ExternalTransformBuilder;
import org.apache.beam.sdk.transforms.PTransform;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PBegin;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.TypeDescriptor;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.apache.beam.sdk.coders.StringUtf8Coder;
import org.joda.time.Instant;

public class SolaceIOBuilder
    implements ExternalTransformBuilder<SolaceIOConfiguration, PBegin, PCollection<String>> {

  @Override
  public PTransform<PBegin, PCollection<String>> buildExternal(SolaceIOConfiguration configuration) {

    return new PTransform<PBegin, PCollection<String>>() {
      @Override
      public PCollection<String> expand(PBegin input) {

        PCollection<SimpleRecord> records = input.apply(
            SolaceIO.read(
              TypeDescriptor.of(SimpleRecord.class),
              SolaceUtils::toSimpleRecord,
              record -> record != null ? record.timestamp : Instant.now()
            )
            .from(Queue.fromName(configuration.getQueueName()))
            .withSempClientFactory(
                BasicAuthSempClientFactory.builder()
                    .host(configuration.getSempHost())
                    .username(configuration.getSempUsername())
                    .password(configuration.getSempPassword())
                    .vpnName(configuration.getVpnName())
                    .build())
            .withSessionServiceFactory(
                BasicAuthJcsmpSessionServiceFactory.builder()
                    .host(configuration.getHost())
                    .username(configuration.getUsername())
                    .password(configuration.getPassword())
                    .vpnName(configuration.getVpnName())
                    .build()));

        PCollection<String> extractedPayloads = records.apply("ExtractPayload", ParDo.of(new ExtractPayloadFn()));
        
        return extractedPayloads;
      }
    };
  }

  static class ExtractPayloadFn extends DoFn<SimpleRecord, String> {
    @ProcessElement
    public void processElement(ProcessContext c) {
        SimpleRecord record = c.element();
        if (record != null) {
            c.output(record.payload);
          } else {
            c.output("");
        }
    }
}
}
