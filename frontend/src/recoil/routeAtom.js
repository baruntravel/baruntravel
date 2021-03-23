import { atom } from "recoil";

export const myRouteCart = atom({
  key: "myRouteCart",
  default: {
    bank1: {
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
    bank2: {
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
    bank3: {
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
    bank4: {
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
    bank5: {
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
      routeName: "TEST",
      places: [
        {
          id: "688578118",
          placeName: "이이잉",
          placeUrl: "http://place.map.kakao.com/688578118",
          addressName: "서울 중구 무교로 6",
          x: "126.97943787116054",
          y: "37.56657026127022",
        },
        {
          id: "508437738",
          placeName: "오오옹",
          placeUrl: "http://place.map.kakao.com/508437738",
          addressName: "서울 중구 세종대로 110",
          x: "126.978244947578",
          y: "37.5662231640346",
        },
        {
          id: "7975883",
          placeName: "키키키",
          placeUrl: "http://place.map.kakao.com/7975883",
          addressName: "서울 중구 을지로 16",
          x: "126.979476558519",
          y: "37.5658314512941",
        },
      ],
    },
    TEST2: {
      routeName: "TEST2",
      places: [
        {
          id: "10144798",
          placeName: "A",
          placeUrl: "http://place.map.kakao.com/10144798",
          addressName: "서울 중구 세종대로 136",
          x: "126.97877403165",
          y: "37.5684910667899",
        },
        {
          id: "8060228",
          placeName: "B",
          placeUrl: "http://place.map.kakao.com/8060228",
          addressName: "서울 중구 세종대로 136",
          x: "126.9782578910947",
          y: "37.56842249719518",
        },
        {
          id: "17695618",
          placeName: "C",
          placeUrl: "http://place.map.kakao.com/17695618",
          addressName: "서울 중구 세종대로 136",
          x: "126.97776884494",
          y: "37.5685990012609",
        },
      ],
    },
  },
});
