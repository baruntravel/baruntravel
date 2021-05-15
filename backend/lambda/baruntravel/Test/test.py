import json
import requests
from fake_useragent import UserAgent

def lambda_handler(event, context):
    ua = UserAgent()
    body = json.loads(event["body"])

    placeId = body["placeId"]
    uri = "https://place.map.kakao.com/main/v/{}".format(placeId)

    headers = {
        'User-Agent': ua.random,
    }
    print(headers)
    res = requests.get(uri, headers=headers)
    try:
        place = json.loads(res.text)

        periodList = []
        if place["basicInfo"].get("openHour", False):
            periodList = place["basicInfo"]["openHour"].get("periodList", [])

        photos = []
        if place["photo"].get("photoList", False):
            photos = [{"imageUrl": item["orgurl"]} for item in place["photo"]["photoList"][0]["list"]]

        results = {
            "placeId": placeId,
            "thumbnail": place["basicInfo"].get("mainphotourl", ""),
            "phone": place["basicInfo"].get("phonenum", ""),
            "openHour": periodList,
            "photos": photos
        }

        return {
            "statusCode": 200,
            "body": results,
        }
    except:
        return {
            "statusCode": 500,
            "body": {
                "uri" : uri
            }
        }

res = lambda_handler({"body": "{\"placeId\" : 1797997961}"}, {})
print(res)
