import { atom } from "recoil";
import { persistAtom } from "./common";

export const userState = atom({
  key: "userState",
  default: {
    isLogin: null,
    name: null, // 내 정보 -> 내id, 내가작성한 route Cart 등 나의 데이터 id들만 가져올 것들?
    email: null,
    avatar: null,
  },
  effects_UNSTABLE: [persistAtom],
});

export const userCart = atom({
  key: "userCart",
  default: [],
  effects_UNSTABLE: [persistAtom],
});

export const userWishList = atom({
  key: "userWishList",
  default: [],
  effects_UNSTABLE: [persistAtom],
});
