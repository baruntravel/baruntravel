import { atom } from "recoil";

export const myRouteCart = atom({
  key: "myRouteCart",
  default: [
    {
      routeName: "test",
    },
  ],
});
