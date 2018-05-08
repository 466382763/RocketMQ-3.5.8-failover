/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.alibaba.rocketmq.broker.processor;

import io.netty.channel.ChannelHandlerContext;

import com.alibaba.rocketmq.broker.BrokerController;
import com.alibaba.rocketmq.broker.out.BrokerOuterAPI;
import com.alibaba.rocketmq.common.BrokerConfig;
import com.alibaba.rocketmq.common.MixAll;
import com.alibaba.rocketmq.common.namesrv.RegisterBrokerResult;
import com.alibaba.rocketmq.common.protocol.RequestCode;
import com.alibaba.rocketmq.common.protocol.body.TopicConfigSerializeWrapper;
import com.alibaba.rocketmq.common.protocol.header.PullMessageResponseHeader;
import com.alibaba.rocketmq.common.protocol.header.namesrv.MasterChangeRequestHeader;
import com.alibaba.rocketmq.remoting.exception.RemotingCommandException;
import com.alibaba.rocketmq.remoting.netty.NettyRequestProcessor;
import com.alibaba.rocketmq.remoting.protocol.RemotingCommand;
import com.alibaba.rocketmq.store.config.BrokerRole;
import com.alibaba.rocketmq.store.config.MessageStoreConfig;


/**
 * @author shijia.wxr
 */
public class MasterChangeProcessor extends AbstractSendMessageProcessor implements NettyRequestProcessor {


    public MasterChangeProcessor(final BrokerController brokerController) {
        super(brokerController);
    }

    @Override
    public RemotingCommand processRequest(ChannelHandlerContext ctx, RemotingCommand request) throws RemotingCommandException {
        switch (request.getCode()) {
            case RequestCode.MASTER_CHANGE:
                return this.processMasterChange(ctx, request);
            default:
                return null;
        }
    }

    @Override
    public boolean rejectRequest() {
        return this.brokerController.getMessageStore().isOSPageCacheBusy();
    }

    private RemotingCommand processMasterChange(final ChannelHandlerContext ctx, final RemotingCommand request)
            throws RemotingCommandException {
    	
    	RemotingCommand response = RemotingCommand.createResponseCommand(MasterChangeRequestHeader.class);
        final MasterChangeRequestHeader requestHeader = (MasterChangeRequestHeader) response.readCustomHeader();
    	
    	brokerController.getMessageStore().updateHaMasterAddress(requestHeader.getHaServerAddr());
        
    	brokerController.getSlaveSynchronize().setMasterAddr(requestHeader.getMasterAddr());
    	
    	brokerController.registerBrokerAll(false, false);
        return null;
    }

}
