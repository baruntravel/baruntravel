import axios from "axios";

export const onAddCart = async (place) => {
  const data = {
    id: place.id,
    name: place.place_name,
    url: place.place_url,
    x: place.x,
    y: place.y,
    address_name: place.road_address_name || place.address_name,
    category: place.category_group_code,
    category_name: place.category_group_name,
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
