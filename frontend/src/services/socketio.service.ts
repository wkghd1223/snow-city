import type { Socket } from "socket.io-client";
import { io } from "socket.io-client";
type ServerToClientEvents = any 
type ClientToServerEvents = any
class SocketIOService {
  socket: Socket<ServerToClientEvents, ClientToServerEvents>;
  constructor() {
    this.socket = io("http://127.0.0.1:8080" );
  }
}

// create an instance/connection here
export const socket = new SocketIOService().socket;