package edu.pdx.cs410J.spaliwal.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import java.util.HashMap;
/**
 * The client-side interface to the airline service
 */
public interface AirlineServiceAsync {

  /**
   * Return all the airlines created on the server
   */
  void displayAllFlights(AsyncCallback<HashMap> async);


  /**
   * Returns the airline details after adding flight to an airline on the server
   * @param name Name of the airline
   * @param f Flight
   */
  void addFlight(String name,Flight f,AsyncCallback<HashMap> async);

  /**
   * Returns the flight details with corresponding source and destination code from the server
   * @param name name of the airline
   * @param src source airport code
   * @param dest destination airport code
   */

  void searchFlight(String name,String src,String dest,AsyncCallback<HashMap> async);

}