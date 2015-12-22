package de.alexholly.util

import java.net.NetworkInterface
import java.net.InetAddress
import java.net.URL
import scala.xml.XML
import scala.io.Source
import scala.sys.process._
import scala.collection.JavaConversions._
import scala.util.Try

object IPManager {

//TESTS
  
//  def main(args: Array[String]) {
//
//    //println(IPManager.isIPv4("128.0.0.1"))
//    //println(IPManager.isIPv4("127.0.0.2"))
//    //println(IPManager.isIPv4("128.0.0.2"))
//    //println(IPManager.isIPv4("127.0.0.0"))
//    //println(IPManager.getLocalIP())
//    //println(IPManager.getInternetIP())
//    //println(IPManager.getHostname())
//    //println(IPManager.getAllIPs())
//  }


  //TODO muss oberservable sein fuer UI
  var ips: List[String] = List[String]()

  initIPS()
  def initIPS() {
    NetworkInterface.getNetworkInterfaces().foreach { networkInterface =>
      networkInterface.getInetAddresses.foreach { ipp =>
        val ip = ipp.getHostAddress().toString()
        if (isIPv4(ip)) {
          ips :+= ip
        }
      }
    }
  }

  /**
   * @return	List of ip's as String (all available networkinterfaces )
   */
  def getAllIPs(): List[String] = {
    ips
  }

  /**
   * @return	The name of the host(Computer) as String, if error then empty String ""
   */
  def getHostname(): String = {
    Try {
      java.net.InetAddress.getLocalHost().getHostName()
    }.getOrElse(
      Try { "hostname".!! }.getOrElse(""))
  }

  /**
   *
   * @return	Your local IP as String if none found "127.0.0.1"
   */
  def getLocalIP(): String = {
    ips.filter { ip =>
      isIPv4(ip) &&
        !(ip.startsWith("127") ||
          ip.endsWith(".1"))
    }.headOption.getOrElse("127.0.0.1")
  }

  def isIPv4(ip: String): Boolean = {
    ip.matches("[1-9][0-9]+.[0-9]+.[0-9]+.[0-9]+")
  }

  /**
   * 		 First try, http://natip.tuxnet24.de/index.php?mode=xml
   * 		 Second, https://api.ipify.org/?format=json
   * 		 @returns Your public ip as String. If any errors you get "127.0.0.1"
   */
  def getInternetIP(): String = {
    var rs = "127.0.0.1"
    try {
      val source = Source.fromURL(new URL("http://natip.tuxnet24.de/index.php?mode=xml"))
      val node = XML.loadString(source.mkString)
      var internetIP = (node \ "ip-address").text
      rs = internetIP
    } catch {
      case e: Throwable =>
        val source = Source.fromURL(new URL("https://api.ipify.org/?format=xml"))
        var internetIP = source.mkString
        rs = internetIP
    }
    rs
  }

}
