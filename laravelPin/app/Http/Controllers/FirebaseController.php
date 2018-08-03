<?php
namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Kreait\Firebase;
use Kreait\Firebase\Factory;
use Kreait\Firebase\ServiceAccount;

class FirebaseController extends Controller
{
    public function index()
    {
        $serviceAccount = ServiceAccount::fromJsonFile(__DIR__.'/laravelfirebase-d86b6-firebase-adminsdk-8xod6-1243f1533d.json');
        $firebase = (new Factory)
        ->withServiceAccount($serviceAccount)
        ->create();

        $db = $firebase->getDatabase();
        $db->getReference('config/website')->set([
          'id'=>1,
          'name'=>'ahmed',
          'phone'=>500000000000
        ]);

        $reference = $db->getReference('config/website');
        $snapshot = $reference->getSnapshot();

        $values = $snapshot->getValue();
        // or
        //$value = $reference->getValue();

         foreach ($values as $value) :
         echo $value ;
        endforeach;
        //echo $value;

        
    }

}
