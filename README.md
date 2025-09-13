# 1) Presentation

This project gather features to easily transfer data from a client to a server or vice versa. this project is based on
the [communication](https://github.com/Pierre-Emmanuel41/communication) project which gives you the possibility
to have the control on how data are exchanged on the lowest level, and on
[protocol](https://github.com/Pierre-Emmanuel41/protocol) project which propose a layout for requests so that the clients
and the server can understand each other.

# 2) Download and compilation

First you need to download this project on your computer. To do so, you can use the following command line:

```git
git clone https://github.com/Pierre-Emmanuel41/messenger.git
```

Executing the batch file <code>deploy.bat</code> will download each dependency and build everything. Finally, you can
add the project as maven dependency to your maven project :

```xml

<dependency>
    <groupId>fr.pederobien</groupId>
    <artifactId>messenger</artifactId>
    <version>2.0-SNAPSHOT</version>
</dependency>
```

# 3) Tutorial

Before creating a client, you will need a client configuration. There are several parameters you can set:
* The layer, ie the lowest level to structure the bytes array to send to the server
* The maximum value of the connection unstable counter (used to prevent from having a crazy connection stuck in an
infinite loop)
* The time after which the connection unstable counter shall be decreased
* The time after which a timeout occurs when trying to connect to the server
* The time after which the connection can retry to connect to the server
* The automatic reconnection flag to indicate if the client can automatically reconnect to the server
* The maximum value of the client unstable counter (used to prevent from having a crazy client stuck in an infinite loop)
* The time after which the client unstable counter shall be decreased
* All requests supported by the client.

Please see below how to create a client configuration:

```java
// In our case we will use an ethernet implementation so the server end point is defined by the IP address and the port number
IEthernetEndPoint endPoint = new EthernetEndPoint("127.0.0.1", 12345);

// The protocol manager gather different protocol versions
// Please see the class MyProtocolManager in folder example/MyProtocolManager
config = Messenger.createClientConfig(MyProtocolManager.getInstance(), "My TCP client", endPoint);

// Setting the layer to use to pack/unpack data.
config.setLayerInitializer(() -> new AesSafeLayerInitializer(new SimpleCertificate()));

// If the connection unstable counter reach 10, the connection will be
// closed automatically
config.setConnectionMaxUnstableCounter(10);

// Decrement the value of the connection unstable counter each 100 ms
config.setConnectionHealTime(100);

// Time in ms after which a timeout occurs when trying to connect to the server
config.setConnectionTimeout(5000);

// The connection wait 1000 ms before retrying to connect with the server
config.setReconnectionDelay(1000);

// Value by default is true
config.setAutomaticReconnection(false);

// If the client unstable counter reach 2, the connection will be
// closed automatically and the client will close itself.
config.setClientMaxUnstableCounter(2);

// Decrement the value of the client unstable counter each 5 ms
config.setClientHealTime(5);

// Adding action to execute when a request has been received
config.addRequestHandler(Identifiers.STRING_ID.getValue(), this::onStringReceived);
config.addRequestHandler(Identifiers.INT_ID.getValue(), this::onIntegerReceived);
config.addRequestHandler(Identifiers.FLOAT_ID.getValue(), this::onFloatReceived);
config.addRequestHandler(Identifiers.PLAYER_ID.getValue(), this::onPlayerReceived);

// Creating the client
client = Messenger.createTcpClient(config);
```

The code above can be encapsulated in your own client class, see
[this client](https://github.com/Pierre-Emmanuel41/messenger/blob/master/src/main/java/fr/pederobien/messenger/example/client/MyCustomTcpProtocolClient.java)


Similarly, you have to create a server configuration before creating a server, There are several parameters you can set:
* The layer, ie the lowest level to structure the bytes array to send to the client
* The maximum value of the client connection unstable counter (used to prevent from having a crazy client connection stuck
in an infinite loop)
* The time after which the client connection unstable counter shall be decreased
* The client validator based on the client end point properties (can be used to blacklist IP address for example)
* The maximum value of the server unstable counter (incremented when an error happened when a new client connects)
* The time after which the server unstable counter shall be decreased

Please see below how to create a server configuration:

```java
// The protocol manager gather different protocol versions
// Please see the class MyProtocolManager in folder example/MyProtocolManager
config = Messenger.createServerConfig(MyProtocolManager.getInstance(), "My TCP server", new EthernetEndPoint(12345));

// Setting the layer to use to pack/unpack data.
// A new layer is defined each time a new client is connected
config.setLayerInitializer(() -> new AesSafeLayerInitializer(new SimpleCertificate()));

// If the unstable counter reach 10, the connection will be automatically closed
config.setConnectionMaxUnstableCounter(10);

// Decrement the value of the unstable counter each 100 ms
config.setConnectionHealTime(100);

// Validate or not if a client is allowed to be connected to the server
config.setClientValidator(this::validateClient);

// If the server unstable counter reach 2, the server will be
// closed automatically as well as each client currently connected.
config.setServerMaxUnstableCounter(2);

// Decrement the value of the server unstable counter each 5 ms
config.setServerHealTime(5);

server = Messenger.createTcpServer(config);
```

The code above can be encapsulated in your own server class, see
[this server](https://github.com/Pierre-Emmanuel41/messenger/blob/master/src/main/java/fr/pederobien/messenger/example/server/MyCustomTcpProtocolServer.java)

Finally, when a client is allowed to connect to the server, the server creates the associated MyCustomTcpProtocolClient,
but on the server side. The custom client set all the request the client supports:

```java
// Adding action to execute when a request has been received
client.addRequestHandler(Identifiers.STRING_ID.getValue(), this::onStringReceived);
client.addRequestHandler(Identifiers.INT_ID.getValue(), this::onIntegerReceived);
client.addRequestHandler(Identifiers.FLOAT_ID.getValue(), this::onFloatReceived);
client.addRequestHandler(Identifiers.PLAYER_ID.getValue(), this::onPlayerReceived);
```

The client implementation, on server side, can be encapsulated in your own client class, see
[this client](https://github.com/Pierre-Emmanuel41/messenger/blob/master/src/main/java/fr/pederobien/messenger/example/server/MyCustomTcpProtocolClient.java)