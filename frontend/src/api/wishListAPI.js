import axios from "axios";

export const onAddWishItem = (wishListId, placeId) => {
  const data = {
    placeId,
  };
  return axios
    .post(`/wishlist/${wishListId}/place`, data)
    .then((res) => true)
    .catch((error) => {
      console.error(error);
      throw new Error(`error`);
    });
};
