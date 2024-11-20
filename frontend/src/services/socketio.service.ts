import type { Socket } from "socket.io-client";
import { io } from "socket.io-client";
type ServerToClientEvents = any 
type ClientToServerEvents = any
class SocketIOService {
  socket: Socket<ServerToClientEvents, ClientToServerEvents>;
  constructor() {
    this.socket = io(import.meta.env.VITE_BACKEND_SOCKET_URL);
  }
}

// create an instance/connection here
export const socket = new SocketIOService().socket;``