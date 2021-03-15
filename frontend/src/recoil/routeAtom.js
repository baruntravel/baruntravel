import { atom } from "recoil";

export const myRouteCart = atom({
  key: "myRouteCart",
  default: {
    test: {
      routeName: "test",
      places: [],
    },
  },
});
