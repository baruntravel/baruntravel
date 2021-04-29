import axios from "axios";

export const onAddCart = async (placeId) => {
  const data = {
    placeId,
  };
  await axios
    .post("/cart/place", data)
    .then((res) => {
      return true;
    })
    .catch((error) => {
      console.error(error);
      return false;
    });
};

export const onReceiveCart = async () => {
  await axios
    .get("/cart/my")
    .then((res) => {
      return res.data.places;
    })
    .catch((error) => {
      console.error(error);
      return false;
    });
};

export const onDeleteCartItem = async (placeId) => {
  await axios
    .get(`/cart/place/${placeId}`)
    .then((res) => {
      return res.data;
    })
    .catch((error) => {
      console.error(error);
    });
};

export const onDeleteCartAll = async () => {
  await axios
    .delete("/cart/place/")
    .then((res) => {
      return true;
    })
    .catch((error) => {
      console.error(error);
      return false;
    });
};
