package edu.uprb.sockets;

/*

 * File: Client.java

 * Author: Víctor M. Martínez 845-09-4440

 * Course: SICI 4037-LJ1, Dr. Juan M. Solá

 * Date: December 7, 2018

 * This class handles the client side operations of the Simon Says App. It takes a given

 * IP Address and port and attempts to communicate to a Simon Says Server App in order to

 * obtain a combination to display to the player.

 */

import android.os.AsyncTask;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;

import edu.uprb.simonsaysapp.ConnectFragment;

/**
 * Created by Víctor M. Martínez on 12/6/2018.
 */

public class Client extends AsyncTask<Void, Void, String> {

    private String destAddress;
    private int destPort;
    private String response = "";
    private TextView textResponse;

    private ConnectFragment parent;
    private int playerCtr;

    /*

     * Creates a client thread with the given IP Address, port

     * and number of clicks allowed in for the combination based on game settings.

     */
    public Client(ConnectFragment parent, String address, int port,
                  TextView response, int playerCtr){
        destAddress = address;
        destPort = port;
        textResponse = response;

        this.parent = parent;
        this.playerCtr = playerCtr;
    }

    @Override
    /*

     * Creates a socket in order to request a combination from the server app.

     * Defines a server given combination if the connection is successful.

     * Collects a connection message from the reply socket sent by the server.

     */
    protected String doInBackground(Void... arg0){

        Socket socket = null;

        try{

            socket = new Socket();

            SocketAddress address = new InetSocketAddress(destAddress, destPort);

            socket.connect(address, 2000);

            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            dos.writeInt(playerCtr);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);

            byte[] buffer = new byte[1024];

            int bytesRead;

            DataInputStream inputStream = new DataInputStream(socket.getInputStream());

            int[] combinationArray = new int[inputStream.readInt()];

            for (int i = 0; i < combinationArray.length; i++) {
                combinationArray[i] = inputStream.readInt();
            }

            parent.combination = combinationArray;

            while((bytesRead = inputStream.read(buffer)) != -1){
                byteArrayOutputStream.write(buffer, 0, bytesRead);
                response += byteArrayOutputStream.toString("UTF-8");
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
            response = "UnknownHostException: " + e.toString();
        } catch (IOException e) {
            e.printStackTrace();
            response = "IOException: " + e.toString() + "\nVerify Sever IP and Port.";
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return response;

    }

    @Override
    /*
     * Displays the message that displays the connection result.
     */
    protected void onPostExecute(String result) {
        textResponse.setText(response);
        super.onPostExecute(result);
    }
}
