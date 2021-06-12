package org.impact.helpers;

import java.net.*;

public class SocketHelper {

    public static String getRemoteIP(Socket s) {

        SocketAddress socketAddress = s.getRemoteSocketAddress();
        InetAddress inetAddress = null;
        if (socketAddress instanceof InetSocketAddress) {
            inetAddress = ((InetSocketAddress) socketAddress).getAddress();
        }

        String return_ = "";

        if (inetAddress instanceof Inet4Address)
            return_ = inetAddress.toString();
        else if (inetAddress instanceof Inet6Address)
            return_ = inetAddress.toString();

        return return_;
    }
}
