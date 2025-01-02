# Chat Server with Java Sockets
This project is part of a university seminar at the University of Hamburg. It explores virtual threads, explaining what they are, how to use them effectively, and the benefits they offer.


## How it works
This project is a chat server implemented using Java Sockets. It is designed to handle multiple client connections simultaneously, allowing clients to communicate with each other in real-time.
The server uses virtual threads to manage client connections efficiently, which helps in handling a large number of concurrent connections without significant performance degradation. Each client is handled by a ClientHandler class that manages the communication between the server and the client. Clients can send messages to all connected clients, list all connected clients, or send private messages to specific clients.

The server maintains a registry of all connected clients using the ClientRegistry class. When a client sends a message, the server broadcasts it to all other clients or directs it to a specific client if it is a private message. 

The server also supports basic commands such as 
1. /exit to disconnect from the server
2. /send to broadcast a message
3. /list to list all connected clients
4. /private to send a private message.

The project includes error handling to manage client disconnections and unknown commands gracefully. The server reads and writes data using BufferedReader and PrintWriter for efficient I/O operations. This project is part of a university seminar at the University of Hamburg, focusing on the use of virtual threads in Java.


## Getting started

### Prerequisites
- Java 17
- IDE (IntelliJ IDEA, Eclipse, etc.)
- Git
- Terminal or Command Prompt
- Telnet _(Don't use Putty as it send additional characters that the server can't handle!)_

### Installation
#### 1. Clone the repository
```bash
git clone https://github.com/eric-liebrecht/socket-chat-server.git
```
#### 2. Open the project in your IDE

#### 3. Run the file ´src/chat/server/Main.java´ to start the chat server

#### 4. Open a terminal or command prompt and run the following command to connect to the server:
```bash
telnet <ip-local-machine> 9876
```
#### 5. You can now start sending messages to the server and other connected clients.