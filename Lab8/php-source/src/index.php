<?php
/**
 * Created by PhpStorm.
 * User: mvmanh
 * Date: 8/21/17
 * Time: 3:50 PM
 */

    header('Access-Control-Allow-Origin: *');
    header('Content-Type: application/json');
    
    $host = 'db';
    $dbName = 'students';
    $username = 'root';
    $password = 'root';

    try{
        $dbCon = new PDO("mysql:host=".$host.";dbname=".$dbName, $username, $password, array(PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION));
        echo "Connected!!";
    }
    catch(PDOException $ex){
        die(json_encode(array('status' => false, 'message' => 'Unable to connect: ' . $ex->getMessage())));
    }

?>
