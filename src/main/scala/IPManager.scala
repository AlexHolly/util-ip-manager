package de.alexholly.util

import java.net.NetworkInterface
import java.util.Enumeration
import java.net.InetAddress
import scala.xml.XML
import scala.io.Source
import java.net.URL
import java.util.ArrayList
import java.util.List
import scala.sys.process._
import scala.collection.JavaConversions._

object IPManager {

  //TESTS
//  def main(args: Array[String]) {
//
//    //      println(IPManager.isIPv4("128.0.0.1"))
//    //      println(IPManager.isIPv4("127.0.0.2"))
//    //      println(IPManager.isIPv4("128.0.0.2"))
//    //    println(IPManager.isIPv4("127.0.0.0"))
//    //      println(IPManager.getLocalIP())
//    println(IPManager.getInternetIP())
//  }

  //////////////////////////////////////////////////////////

  //TODO muss oberservable sein fuer UI
  var ips: List[String] = new ArrayList[String]()

  initIPS()
  def initIPS() {
    NetworkInterface.getNetworkInterfaces().foreach { networkInterface =>
      networkInterface.getInetAddresses.foreach { ipp =>
        val ip = ipp.getHostAddress().toString()
        if (isIPv4(ip.toString())) {
          ips.add(ip.toString())
        }
      }
    }
  }

  def getAllIPs(): List[String] = {
    ips
  }

  def getHostname(): String = {
    try {
      return java.net.InetAddress.getLocalHost().getHostName()
    } catch {
      case e: java.net.UnknownHostException =>
        return "hostname".!!
    }
  }

  def getLocalIP(): String = {
    ips.foreach { ip =>
      if (isIPv4(ip) && !(ip.startsWith("127") || ip.endsWith(".1"))) {
        return ip
      }
    }
    return null
  }

  def isIPv4(ip: String): Boolean = {
    ip.matches("[1-9][0-9]+.[0-9]+.[0-9]+.[0-9]+")
  }

  /*
   * 		 First try, http://natip.tuxnet24.de/index.php?mode=xml
   * 		 Second, https://api.ipify.org/?format=json
   * 		 else returns your public ip if any errors you get "127.0.0.1"
   */
  def getInternetIP(): String = {
    try {
      val source = Source.fromURL(new URL("http://natip.tuxnet24.de/index.php?mode=xml"))
      val node = XML.loadString(source.mkString)
      var internetIP = (node \ "ip-address").text
      return internetIP
    } catch {
      case e: Throwable =>
        val source = Source.fromURL(new URL("https://api.ipify.org/?format=xml"))
        var internetIP = source.mkString
        return internetIP
    }
    return "127.0.0.1"
  }

}
