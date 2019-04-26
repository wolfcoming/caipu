package com.example.userCentercomponent.websocket

import com.infoholdcity.basearchitecture.self_extends.Klog
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.lang.Exception
import java.net.URI

class MyWebSocket(serverUri: URI?) : WebSocketClient(serverUri) {
    override fun onOpen(handshakedata: ServerHandshake?) {
        Klog.e(contents = "onOpen")
    }

    override fun onClose(code: Int, reason: String?, remote: Boolean) {
        Klog.e(contents = "onClose")
    }

    override fun onMessage(message: String?) {
        Klog.e(contents = "onMessage")
    }

    override fun onError(ex: Exception?) {
        Klog.e(contents = "onError")
    }
}