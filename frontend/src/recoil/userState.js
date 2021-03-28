import { atom } from "recoil";

export const userState = atom({
  key: "userState",
  default: {
    isLogin: null, // 내 정보 -> 내id, 내가작성한 route Cart 등 나의 데이터 id들만 가져올 것들?
    // me에 id들을 저장하면 리스트 중에서 내것이 있는 지 찾아보기 쉽다.
    userInfo: null,
  },
});
