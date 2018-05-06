# *Client and Server Chat Application(using SSL)*

![Project Logo](https://github.com/loveshah751/Client_Server-SSL-/blob/master/images/seo.png)

**A clever implementation can actually hide the mess of encryption, decryption and key exchange protocol entirely. As far as the server is concerned, it only wants to know if the client is authorized and receive and send data in clear-text even though the actual bytes on the network are encrypted. As far as the client is concerned, it only wants to know that it is connected to the real server and also wants to exchange data in clear-text even though the physical bytes transmitted are encrypted.**

## Software Requirements / Development Using Eclipse
1.) **JDK 1.8+**

2.) **Clone the project from its GitHub repository.**

3.) **In Eclipse, select File -> open project from File Sysetm ... -> give the relative path where you clone the REPO --> user/desktop/SSL_Connection.**

4.) **Run the server.java class first and then run the client.java class to see the desired output.**



## Let us investigate each point in a bit more detail.

**Server's perspective** 

When ever user or client try's to connect and start chatting with server, Firstly Client need to be authenicate by the server by client **username** and **Company_Name**. If Server Authenicate Client will gets Greeting message **"Welcome Username"** else Server will closed the connection with error message **"Server closed due to unauthorised access"**.

**This is Server side perpective**

![MacDown logo](https://github.com/loveshah751/Client_Server-SSL-/blob/master/images/error_server.png)

**This is Client side perpective**

![MacDown logo](https://github.com/loveshah751/Client_Server-SSL-/blob/master/images/error_client.png)

**Successfull Authenication**

If the server Successfull authenicate the client below messages will be appeared on both client and server. 

**This is Server side perpective**

![MacDown logo](https://github.com/loveshah751/Client_Server-SSL-/blob/master/images/authorise_server.png)

**This is Client side perpective**

![MacDown logo](https://github.com/loveshah751/Client_Server-SSL-/blob/master/images/authorise_client.png)


## Message Exchangement 

Once Handshake takes place, server start chatting with client and these chatting messages are transmitted securely using custom Hash methods define **Hash.java** class and assembled the transmitted packet and then on server side that packet is disassembled and retrieve the original message.








