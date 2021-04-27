import json
import requests

def lambda_handler(event, context):
    body = json.loads(event["body"])

    placeId = body["placeId"]
    uri = "https://place.map.kakao.com/main/v/{}".format(placeId)

    res = requests.get(uri)

    place = json.loads(res.text)

    periodList = []
    if place["basicInfo"].get("openHour", False):
        periodList = place["basicInfo"]["openHour"].get("periodList", [])

    photos = []
    if place["photo"].get("photoList", False):
        photos = [ { "imageUrl": item["orgurl"] } for item in place["photo"]["photoList"][0]["list"] ]


    results = {
        "thumbnail": place["basicInfo"].get("mainphotourl", ""),
        "phone": place["basicInfo"].get("phonenum", ""),
        "openHour": periodList,
        "photos": photos
    }

    return {
        "statusCode": 200,
        "body": results,
    }
