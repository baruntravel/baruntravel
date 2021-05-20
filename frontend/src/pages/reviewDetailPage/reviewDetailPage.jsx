import React from "react";
import DetailHeader from "../../components/detailHeader/detailHeader";
import ReviewCard from "../../components/reviewComponents/reviewList/reviewCard/reviewCard";
import styles from "./reviewDetailPage.module.css";

const ReviewDetailPage = (props) => {
  const review = {
    id: 1,
    content: "경로리뷰내용1",
    score: 4,
    creator: {
      name: "테스트유저",
      avatar:
        "https://s3.ap-northeast-2.amazonaws.com/s3.baruntravel.me/NVKd7InpFVTtespa79wvLKMj7MyGdovTroWJI7nInwpF4symIR4J3VQLpTxn.png",
    },
    likeCount: 0,
    likeCheck: false,
    files: [
      {
        url: "https://i.pinimg.com/564x/d7/ec/75/d7ec75c9e68873ee75b734ac4ab09ced.jpg",
      },
      {
        url: "https://i.pinimg.com/474x/30/5a/21/305a216481dfaaec10fd59cf1f667652.jpg",
      },
      {
        url: "https://i.pinimg.com/474x/5f/a2/8e/5fa28eae2bdebd6ab2d30690304927b9.jpg",
      },
      {
        url: "https://i.pinimg.com/474x/a6/56/70/a65670944d4bf492a3a71c4a95bb3910.jpg",
      },
      {
        url: "https://i.pinimg.com/474x/e8/58/f3/e858f330363c0fb4240ca8cad087f74d.jpg",
      },
      {
        url: "https://i.pinimg.com/474x/c5/eb/47/c5eb47f58dd27a764f88551151f54893.jpg",
      },
      {
        url: "https://i.pinimg.com/474x/5f/a2/8e/5fa28eae2bdebd6ab2d30690304927b9.jpg",
      },
    ],
    createdAt: "2021-04-14 09:00:00",
    updatedAt: "2021-04-19 09:00:00",
  };
  return (
    <div className={styles.ReviewDetailPage}>
      <DetailHeader />
      <ReviewCard isDetail={true} review={review} />
    </div>
  );
};

export default ReviewDetailPage;
