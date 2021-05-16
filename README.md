# extremely-simple-ad-api-with-akka-http
Ridiculously simple ad api using Akka HTTP.

This is Akka HTTP version of [this](https://github.com/sankaku/extremely-simple-ad-api-with-actix).

## Build/Run
```sh
docker-compose up -d
cd app
sbt run
```

## API
### GET `/deliver`
Delivers ads.

The IDs of delivered ads are UUIDs and recorded in Redis as hashes.  
The key of Redis hash is like `id:168a476d-43ec-4b15-892b-2d8102824928`.

- `num`  
  [_Optional_, _Default_ = 5] Integer. The number of ads to be returned.

```sh
$ curl --silent localhost:8080/deliver?num=3 | jq
{
  "errors": [],
  "message": {
    "ads": [
      {
        "id": "168a476d-43ec-4b15-892b-2d8102824928"
      },
      {
        "id": "6b2667fb-63fa-4109-a093-b9082dc534b0"
      },
      {
        "id": "7aecb4bc-897b-470d-981f-350252fe9215"
      }
    ]
  },
  "success": true
}
```

### POST `/cv`
Tracks a conversion.

- `id`  
  [_Mandatory_] UUID. An ID of the fetched ads in `/deliver`.

```sh
$ curl --silent -XPOST localhost:8080/cv?id=168a476d-43ec-4b15-892b-2d8102824928 | jq
{
  "errors": [],
  "message": {
    "id": "168a476d-43ec-4b15-892b-2d8102824928"
  },
  "success": true
}
```

## License
MIT License
