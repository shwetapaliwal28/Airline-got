package edu.pdx.cs410J.spaliwal.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import java.util.HashMap;
/**
 * A GWT remote service that returns a dummy airline
 */
@RemoteServiceRelativePath("airline")
public interface AirlineService extends RemoteService {

  /**
   * Return all the airlines created on the server
   */
  public HashMap displayAllFlights();

  /**
   * Returns the airline details after adding flight to an airline on the server
   * @param name Name of the airline
   * @param f Flight
   */
  public HashMap addFlight(String name,Flight f);

  /**
   * Returns the flight details with corresponding source and destination code from the server
   * @param name name of the airline
   * @param src source airport code
   * @param dest destination airport code
   */

  public HashMap searchFlight(String name,String src,String dest);

}
