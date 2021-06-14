import axios from "axios";

export const onAddNewMyWish = (name) => {
  const data = {
    name,
  };
  return axios
    .post(`/wishlist`, data)
    .then((res) => true)
    .catch((error) => {
      console.error(error);
      throw new Error(`error : ${error}`);
    });
};

export const onAddWishItem = (wishListId, placeId) => {
  const data = {
    placeId,
  };
  return axios
    .post(`/wishlist/${wishListId}/place`, data)
    .then((res) => true)
    .catch((error) => {
      console.error(error);
      throw new Error(`error : ${error}`);
    });
};

export const onReceiveWishList = () => {
  return axios
    .get(`/wishlist/my`)
    .then((res) => res.data)
    .catch((error) => {
      console.error(error);
      throw new Error(`error : ${error}`);
    });
};