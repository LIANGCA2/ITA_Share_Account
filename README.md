# ITA_Share_Account
### Add account   
Action: Post  
URL: /api/v1/accounts
Request: 
```
{
	"user":{"id":1},
	"type":{"id":1},
	"amount":11,
	"accountKind":"1",
	"isDelete":"0"
}
```

### Delete acount
Action: Delete  
URL: /api/v1/accounts/{id}   
Success: HttpStatus.NO_CONTENT

### Get Account By ID
Action: Get     
URL: /api/v1/accounts/{id}   
Response: 

### Get Accounts By Month
Action: Get     
URL: /api/v1/accounts/month/{time}   
Response: 
``` http://localhost:8888/api/v1/accounts/month/2019-03

```

### Update Account By ID
Action: Patch     
URL: /api/v1/accounts/{id}   
Request:   
Response: 


