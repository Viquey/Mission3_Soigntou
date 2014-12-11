<?php



$jsonUploaded = file_get_contents("fromData.json");

$jsonDecoded = json_decode($jsonUploaded);

foreach($jsonDecoded as $item){
	unset($item->{'datasetid'});
	unset($item->{'recordid'});
	unset($item->{'geometry'});
	unset($item->{'record_timestamp'});
	unset($item->{'fields'}->{'departement'});
	unset($item->{'fields'}->{'libdepartement'});
	unset($item->{'fields'}->{'wgs'});
	unset($item->{'fields'}->{'dateautor'});
	unset($item->{'fields'}->{'datemaj'});
	unset($item->{'fields'}->{'dateouv'});
	unset($item->{'fields'}->{'nofinesset'});
	unset($item->{'fields'}->{'compldistrib'});
	unset($item->{'fields'}->{'nofinessej'});
}
 
$jsonReturned=json_encode($jsonDecoded , JSON_FORCE_OBJECT);

$jsonFile = fopen("data.json", "x+");
fputs($jsonFile, $jsonReturned);
fclose($jsonFile);

?>


