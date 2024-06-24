# Address API

## Create Adderss 

Endoint : POST /api/contacts/{idContact}/addreses

Request Header :
- X-API-TOKEN : Token (Mandatory)


Request body:
```json
{
    "streat":"jalan apa",
    "city": "kota",
    "provici": "Provinsi apa",
    "country": "country apa",
    "postalCode": "123434",

}
```

Response body (Success) : 

```json
{
    "data":{
        "id":"random (String)",
        "streat":"jalan apa",
        "city": "kota",
        "provici": "Provinsi apa",
        "country": "country apa",
        "postalCode": "123434",
    }
}
```

Response body (Failed ) : 

```json
{
    "error":"Contact is not found"
}
```

## Update Adders

Endoint : PUT /api/contact/addreses/{idContact}

Request Header :
- X-API-TOKEN : Token (Mandatory)


Request body:
```json
{
    "streat":"jalan apa",
    "city": "kota",
    "provici": "Provinsi apa",
    "country": "country apa",
    "postalCode": "123434",

}
```

Response body (Success) : 

```json
{
    "data":{
        "id":"random (String)",
        "streat":"jalan apa",
        "city": "kota",
        "provici": "Provinsi apa",
        "country": "country apa",
        "postalCode": "123434",
    }
}
```

Response body (Failed ) : 

```json
{
    "error":"Address is not found"
}
```


## Get Adders

Endoint : GET /api/contacts/{idContact}/addreses/{idAddreses}

Request Header :
- X-API-TOKEN : Token (Mandatory)


Response body (Success) : 

```json
{
    "data":{
        "id":"random (String)",
        "streat":"jalan apa",
        "city": "kota",
        "provici": "Provinsi apa",
        "country": "country apa",
        "postalCode": "123434",
    }
}


Response body (Failed ) : 

```json
{
    "error":"Address is not found"
}
```

## Remove Adders

Endoint : DELETE /api/contacts/{idContact}/addreses/{idAddreses}

Request Header :
- X-API-TOKEN : Token (Mandatory)


Response body (success):
```json
{
    "streat":"jalan apa",
    "city": "kota",
    "provici": "Provinsi apa",
    "country": "country apa",
    "postalCode": "123434",

}
```


Response body (Failed ) : 

```json
{
    "error":"Address is not found"
}
```

## List Adders 

Endoint : GET /api/contacts/{idContact}/addreses

Request Header :
- X-API-TOKEN : Token (Mandatory)


Response body (success):
```json
{
    "data":{
        "id":"random (String)",
        "streat":"jalan apa",
        "city": "kota",
        "provici": "Provinsi apa",
        "country": "country apa",
        "postalCode": "123434",
    }
}
```


Response body (Failed ) : 

```json
{
    "error":"Contact is not found"
}
```


