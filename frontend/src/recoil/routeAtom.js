import { atom } from "recoil";

export const testPlaces = atom({
  key: "testPlaces",
  default: [
    {
      id: "688578118",
      placeName: "BNK저축은행 리테일금융센터",
      placeUrl: "http://place.map.kakao.com/688578118",
      address_name: "서울 중구 무교로 6",
      x: "126.97943787116054",
      y: "37.56657026127022",
    },
    {
      id: "508437738",
      placeName: "신한은행 서울시청금융센터",
      placeUrl: "http://place.map.kakao.com/508437738",
      address_name: "서울 중구 세종대로 110",
      x: "126.978244947578",
      y: "37.5662231640346",
    },
    {
      id: "7975883",
      placeName: "신한은행 서울광장출장소",
      placeUrl: "http://place.map.kakao.com/7975883",
      address_name: "서울 중구 을지로 16",
      x: "126.979476558519",
      y: "37.5658314512941",
    },
  ],
});
export const myRouteCart = atom({
  key: "myRouteCart",
  default: {
    route1: {
      routeName: "은행 여기저기 탐방",
      location: "중구",
      year: "2021",
      month: "3",
      date: "6",
      places: [
        {
          id: "688578118",
          placeName: "BNK저축은행 리테일금융센터",
          placeUrl: "http://place.map.kakao.com/688578118",
          addressName: "서울 중구 무교로 6",
          x: "126.97943787116054",
          y: "37.56657026127022",
        },
        {
          id: "508437738",
          placeName: "신한은행 서울시청금융센터",
          placeUrl: "http://place.map.kakao.com/508437738",
          addressName: "서울 중구 세종대로 110",
          x: "126.978244947578",
          y: "37.5662231640346",
        },
        {
          id: "7975883",
          placeName: "신한은행 서울광장출장소",
          placeUrl: "http://place.map.kakao.com/7975883",
          addressName: "서울 중구 을지로 16",
          x: "126.979476558519",
          y: "37.5658314512941",
        },
      ],
    },
    route2: {
      routeName: "bank2",
      places: [
        {
          id: "10144798",
          placeName: "KB국민은행 무교지점",
          placeUrl: "http://place.map.kakao.com/10144798",
          addressName: "서울 중구 세종대로 136",
          x: "126.97877403165",
          y: "37.5684910667899",
        },
        {
          id: "8060228",
          placeName: "전북은행 서울지점",
          placeUrl: "http://place.map.kakao.com/8060228",
          addressName: "서울 중구 세종대로 136",
          x: "126.9782578910947",
          y: "37.56842249719518",
        },
        {
          id: "17695618",
          placeName: "신한은행 PWM서울파이낸스센터",
          placeUrl: "http://place.map.kakao.com/17695618",
          addressName: "서울 중구 세종대로 136",
          x: "126.97776884494",
          y: "37.5685990012609",
        },
      ],
    },
    route3: {
      routeName: "bank3",
      places: [
        {
          id: "688578118",
          placeName: "BNK저축은행 리테일금융센터",
          placeUrl: "http://place.map.kakao.com/688578118",
          addressName: "서울 중구 무교로 6",
          x: "126.97943787116054",
          y: "37.56657026127022",
        },
        {
          id: "508437738",
          placeName: "신한은행 서울시청금융센터",
          placeUrl: "http://place.map.kakao.com/508437738",
          addressName: "서울 중구 세종대로 110",
          x: "126.978244947578",
          y: "37.5662231640346",
        },
        {
          id: "7975883",
          placeName: "신한은행 서울광장출장소",
          placeUrl: "http://place.map.kakao.com/7975883",
          addressName: "서울 중구 을지로 16",
          x: "126.979476558519",
          y: "37.5658314512941",
        },
      ],
    },
    route4: {
      routeName: "bank4",
      places: [
        {
          id: "688578118",
          placeName: "BNK저축은행 리테일금융센터",
          placeUrl: "http://place.map.kakao.com/688578118",
          addressName: "서울 중구 무교로 6",
          x: "126.97943787116054",
          y: "37.56657026127022",
        },
        {
          id: "508437738",
          placeName: "신한은행 서울시청금융센터",
          placeUrl: "http://place.map.kakao.com/508437738",
          addressName: "서울 중구 세종대로 110",
          x: "126.978244947578",
          y: "37.5662231640346",
        },
        {
          id: "7975883",
          placeName: "신한은행 서울광장출장소",
          placeUrl: "http://place.map.kakao.com/7975883",
          addressName: "서울 중구 을지로 16",
          x: "126.979476558519",
          y: "37.5658314512941",
        },
      ],
    },
    route5: {
      routeName: "bank5",
      places: [
        {
          id: "688578118",
          placeName: "BNK저축은행 리테일금융센터",
          placeUrl: "http://place.map.kakao.com/688578118",
          addressName: "서울 중구 무교로 6",
          x: "126.97943787116054",
          y: "37.56657026127022",
        },
        {
          id: "508437738",
          placeName: "신한은행 서울시청금융센터",
          placeUrl: "http://place.map.kakao.com/508437738",
          addressName: "서울 중구 세종대로 110",
          x: "126.978244947578",
          y: "37.5662231640346",
        },
        {
          id: "7975883",
          placeName: "신한은행 서울광장출장소",
          placeUrl: "http://place.map.kakao.com/7975883",
          addressName: "서울 중구 을지로 16",
          x: "126.979476558519",
          y: "37.5658314512941",
        },
      ],
    },
  },
});

export const usersRouteItems = atom({
  key: "userRouteCart",
  default: {
    TEST1: {
      routeName: "레트로 데이트",
      creator: "nth9708",
      places: [
        {
          id: "688578118",
          placeName: "ㅇㅇ카페",
          placeUrl: "http://place.map.kakao.com/688578118",
          addressName: "서울 중구 무교로 6",
          x: "126.97943787116054",
          y: "37.56657026127022",
        },
        {
          id: "508437738",
          placeName: "ㅇㅇ식당",
          placeUrl: "http://place.map.kakao.com/508437738",
          addressName: "서울 중구 세종대로 110",
          x: "126.978244947578",
          y: "37.5662231640346",
        },
        {
          id: "7975883",
          placeName: "ㅇㅇ맥주",
          placeUrl: "http://place.map.kakao.com/7975883",
          addressName: "서울 중구 을지로 16",
          x: "126.979476558519",
          y: "37.5658314512941",
        },
      ],
    },
    TEST2: {
      routeName: "여친과 데이트",
      creator: "kiki123",
      places: [
        {
          id: "10144798",
          placeName: "ABC카페",
          placeUrl: "http://place.map.kakao.com/10144798",
          addressName: "서울 중구 세종대로 136",
          x: "126.97877403165",
          y: "37.5684910667899",
        },
        {
          id: "8060228",
          placeName: "ABC식당",
          placeUrl: "http://place.map.kakao.com/8060228",
          addressName: "서울 중구 세종대로 136",
          x: "126.9782578910947",
          y: "37.56842249719518",
        },
        {
          id: "17695618",
          placeName: "ABC맥주",
          placeUrl: "http://place.map.kakao.com/17695618",
          addressName: "서울 중구 세종대로 136",
          x: "126.97776884494",
          y: "37.5685990012609",
        },
      ],
    },
  },
});
