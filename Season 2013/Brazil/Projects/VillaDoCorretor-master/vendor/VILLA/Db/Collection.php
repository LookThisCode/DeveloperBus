<?php

abstract class Collection {

    protected $db;
    protected $collection;

    public function __construct($db)
    {
        $this->db = $db;
        $this->collection = $db->selectCollection($this->collection);
    }

    public function fetchAll($filter =  [], $limit = null, $skip=null)
    {
        $cursor = $this->collection->find($filter);
        $cursor->sort(["preco" => 1]);
        if($limit)
            $cursor->limit($limit);
        if($skip)
            $cursor->skip($skip);

        $imoveis = [];
        while($document = $cursor->getNext())
            $imoveis[] = $document;

        return $imoveis;
    }

    public function find($id){

        $cursor = $this->collection->findOne(["_id" => new \MongoId($id)]);
        return $cursor;
    }

    public function findFull($parameter, $limit){

       $imoveis = [];
       $cursor = $this->db->command(["text" => "imoveis", "search" => "$parameter", "limit" => $limit]);
       $arr = $cursor["results"];
        $a = 0;
        foreach($arr as $me){
            foreach($me as $te){
                if($a % 2 != 0)
                    $imoveis[] = $te;
                $a++;
            }
        }
        return $imoveis;
    }
    
    public function findBairroByMunic($filter =  [])
    {
        $cursor = $this->collection->find($filter);
        $cursor->limit("800")->sort(["bairro" => 1]);
        $bairro = [];
        while($document = $cursor->getNext())
            $bairro[] = $document;

        return $bairro;
    }
    
    public function fetchAllMunic()
    {
        $cursor = $this->db->command(["distinct" => "bairro", "key" => "municipio"]);

        return $cursor['values'];
    }
}