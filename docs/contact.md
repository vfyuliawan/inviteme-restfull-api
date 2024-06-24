# Contact API


## Create Contact

Endpoint :POST api/contacts

Request Header : 
- X-API-TOKEN : Token (Mandatory)

Request Body :

```json
{
    "id_contact":"random (string)",
    "firstName":"vicky",
    "lastName":"fadilla",
    "emai":"cang@gamil.com",
    "phone":"08123821038231",
}
```

Response Body (Success) :

```json
{
    "data":{
        "id_contact":"random (string)",
        "firstName":"vicky",
        "lastName":"fadilla",
        "emai":"cang@gamil.com",
        "phone":"08123821038231",
    }
}
```

Response Body (Failed) :


```json
{
   "errors":"Email Format invalid, phone format invalid"
}

```


## Update Conctact


Endpoint : PUT /api/contacts/{idContact}

Request Header : 
- X-API-TOKEN : Token (Mandatory)

Request Body :
```json
{
        "id_contact":"random (string)",
        "firstName":"vicky",
        "lastName":"fadilla",
        "emai":"cang@gamil.com",
        "phone":"08123821038231",
}
```

Response Body (Success) :

```json
{
    "data":{
        "id_contact":"random (string)",
        "firstName":"vicky",
        "lastName":"fadilla",
        "emai":"cang@gamil.com",
        "phone":"08123821038231",
    }
}
```

Response Body (Failed) :


```json
{
   "errors":"Email Format invalid, phone format invalid"
}
```



## Get Contact

Endpoint : GET /api/contacts/{idContact}

Request Header : 
- X-API-TOKEN : Token (Mandatory)


Response Body (Success) :

```json
{
    "data":{
        "id_contact":"random (string)",
        "firstName":"vicky",
        "lastName":"fadilla",
        "emai":"cang@gamil.com",
        "phone":"08123821038231",
    }
}
```

Response Body (Failed, 401) :
```json
{
    "errors":"contact is not found"
}
```



 
## Search Contact

Endpoint : GET /api/contacts

Query Param:
- name: String, contact name combine firstname and last name, using like query (optional)
- phone: String, contact phone, using like query (optional)
- email: String, contact email, using like query (optional)
- page : Integer, start from 0, default (0)
- size: Ingeger, 10, default (10)

Request Header : 
- X-API-TOKEN : Token (Mandatory)



Response Body (Success) :
```json
{
    "data":{
        [
            "data":{
                    "id_contact":"random (string)",
                    "firstName":"vicky",
                    "lastName":"fadilla",
                    "emai":"cang@gamil.com",
                    "phone":"08123821038231",
                }
        ],
        "paging":{
            "currentPage" : 0,
            "totalPage": 10,
            "size":10
        }
    }
}
```



## Remove Contact

Endpoint : DELETE /api/contacts/{idContact}

Request Header : 
- X-API-TOKEN : Token (Mandatory)

Response Body (Success) :
```json
{
    "data":"OK"
}
```

Response Body (Failed, 401) :
```json
{
    "errors":"contact is not found"
}
```