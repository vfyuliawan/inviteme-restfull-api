# User API Spec

## Register User 

- Endpoint: POST /api/users
- Request Body:

```json
{
    "username":"",
    "password":"",
    "name":""
}
```

-Response body (Success) : 
```json
{
    "data":"OK"
}
```
-Response body (Failed) : 
```json
{
    "data":"KO",
    "errors":"Username Must Not Blank"
}
```

## Login User 

- Endpoint: POST /api/auth/login

- Request Body:

```json
{
    "username":"cang",
    "password":"rahasia",
}
```

-Response body (Success) : 
```json
{
    "data":{
        "token":"TOKEN",
        "expired_at":24324535345345345 //milisecond
    }
}
```
-Response body (Failed) : 
```json
{
    "errors":"Username Or Password wrong"
}
```


## Get User 

- Endpoint: GET /api/users/current

- Request Header
- X-API-TOKEN: Token (Mandatory)

  
- Response body (Success) : 
```json
{
    "data":{
        "username":"cang",
        "name":"vicky fadilla yuliawan"
    }
}
```
- Response body (Failed, 401) : 
```json
{
    "errors":"Unauthorized"
}
```

## Update User  

- Endpoint: PATCH /api/users/current

- Request body: 
```json
{
    "name":"Cang", //put if only want to update
    "password":"new password", //put if only want to update password
}
```

- Response body (Success) : 
```json
{
    "data":{
        "username":"cang",
        "name":"vicky fadilla yuliawan"
    }
}
```
- Response body (Failed, 401) : 
```json
{
    "errors":"Unauthorized"
}
```


## Logout User 

- Endpoint: DELETE /api/auth/logout

- Request Header :
- X-API-TOKEN: Token (Mandatory)

- Response body (Success) : 
```json
{
    "data":"OK"
}
```