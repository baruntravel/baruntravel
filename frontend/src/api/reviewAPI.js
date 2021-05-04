import axios from "axios";

export const onReviewUpload = async (routeId, files, content, score) => {
  const data = {
    files,
    content,
    score,
  };
  await axios
    .post(`/route/${routeId}/review`, data, {
      headers: { "Content-Type": "multipart/form-data" },
    })
    .then((res) => true)
    .catch((error) => {
      console.error(error);
      throw new Error(`unExpected Error ${error}`);
    });
};

export const onReceiveReview = async (routeId) => {
  const result = await axios
    .get(`/route/${routeId}/reviews`)
    .then((res) => res.data.reviews)
    .catch((error) => {
      console.log(error);
      throw new Error(`unExpected Error ${error}`);
    });
  return result;
};

export const onEditReview = async (routeId, files, content, score) => {
  const data = {
    files,
    content,
    score,
  };
  return await axios
    .post(`/route/review/${routeId}`, data, {
      headers: { "Content-Type": "multipart/form-data" },
    })
    .then((res) => true)
    .catch((error) => {
      console.error(error);
      throw new Error(`unExpected Error ${error}`);
    });
};

export const onDeleteReview = async (routeId) => {
  await axios
    .delete(`/route/review/${routeId}`)
    .then((res) => true)
    .catch((error) => {
      console.error(error);
      throw new Error(`unHandled error ${error}`);
    });
};
