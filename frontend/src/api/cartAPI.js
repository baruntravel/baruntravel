import axios from "axios";

export const onAddCart = (place) => {
  const data = {
    id: Number(place.id),
    name: place.place_name,
    url: place.place_url,
    x: Number(place.x),
    y: Number(place.y),
    address: place.road_address_name || place.address_name,
    categoryId: place.category_group_code,
    categoryName: place.category_group_name,
  };
  return axios
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
  return await axios
    .get("/cart/my")
    .then((res) => {
      return res.data.places;
    })
    .catch((error) => {
      console.error(error);
      return false;
    });
};

export const onDeleteCart = (placeId) => {
  return axios
    .delete(`/cart/place/${placeId}`)
    .then((res) => {
      return true;
    })
    .catch((error) => {
      console.error(error);
      return false;
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

export const onWriteMemo = async (placeId, memo) => {
  const data = {
    memo,
  };
  const result = await axios
    .put(`/cart/place/${placeId}/memo`, data)
    .then((res) => {
      return true;
    })
    .catch((error) => {
      console.error(error);
      return false;
    });
  return result;
};

export const onEditOrder = (firstPlaceId, secondPlaceId) => {
  const data = {
    firstPlaceId,
    secondPlaceId,
  };
  return axios
    .put(`/cart/place`, data)
    .then((res) => true)
    .catch((error) => {
      console.error(error);
      throw new Error(`unExpected error ${error}`);
    });
};
