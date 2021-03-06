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
package org.apache.beam.runners.flink.streaming;

import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.flink.streaming.api.operators.Output;
import org.apache.flink.streaming.api.operators.StreamSource;
import org.apache.flink.streaming.api.transformations.OneInputTransformation;
import org.apache.flink.streaming.api.transformations.StreamTransformation;
import org.apache.flink.streaming.runtime.streamrecord.StreamRecord;
import org.apache.flink.streaming.runtime.streamstatus.StreamStatusMaintainer;

/** {@link StreamSource} utilities, that bridge incompatibilities between Flink releases. */
public class StreamSources {

  /**
   * Backward compatibility helper for {@link OneInputTransformation} `getInput` method, that has
   * been removed in Flink 1.12.
   *
   * @param source Source to get single input from.
   * @return Input transformation.
   */
  public static <T> StreamTransformation<T> getOnlyInput(OneInputTransformation<T, ?> source) {
    return source.getInput();
  }

  public static <OutT, SrcT extends SourceFunction<OutT>> void run(
      StreamSource<OutT, SrcT> streamSource,
      Object lockingObject,
      StreamStatusMaintainer streamStatusMaintainer,
      Output<StreamRecord<OutT>> collector)
      throws Exception {
    streamSource.run(lockingObject, streamStatusMaintainer, collector);
  }
}
