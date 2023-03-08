```
-- tkeUser
{
"kind": "IdentityProvider",
"apiVersion": "auth.tkestack.io/v1",
"metadata": {
"name": "${userName}"
},
"spec": {
"administrators": [
"admin"
],
"config": "{}",
"name": "${userName}",
"type": "tke"
}
}

```