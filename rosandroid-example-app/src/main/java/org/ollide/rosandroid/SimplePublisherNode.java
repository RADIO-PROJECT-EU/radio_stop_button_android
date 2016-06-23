/*
 * Copyright (C) 2014 Oliver Degener.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package org.ollide.rosandroid;

import android.util.Log;

import org.ros.concurrent.CancellableLoop;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.NodeMain;
import org.ros.node.topic.Publisher;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SimplePublisherNode extends AbstractNodeMain implements NodeMain {

    private static final String TAG = SimplePublisherNode.class.getSimpleName();

    @Override
    public GraphName getDefaultNodeName() {
        return GraphName.of("EmergencyStopper");
    }

    @Override
    public void onStart(ConnectedNode connectedNode) {
        final Publisher<std_msgs.Int32> publisher = connectedNode.newPublisher(GraphName.of("emergency_stop"), std_msgs.Int32._TYPE);

        final CancellableLoop loop = new CancellableLoop() {
            @Override
            protected void loop() throws InterruptedException {

                std_msgs.Int32 stop = publisher.newMessage();
                stop.setData(1);
                publisher.publish(stop);

                Thread.sleep(500);
            }
        };
        connectedNode.executeCancellableLoop(loop);
    }

}
