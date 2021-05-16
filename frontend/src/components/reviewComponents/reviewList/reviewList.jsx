import React, { useCallback, useRef, useState } from "react";
import ReviewCard from "./reviewCard/reviewCard";
import styles from "./reviewList.module.css";
import { EditTwoTone } from "@ant-design/icons";
import DeleteConfirm from "../../common/deleteConfirm/deleteConfirm";
import { Drawer } from "antd";
import ReviewForm from "../reviewForm/reviewForm";

const ReviewList = ({
  onOpenPortalAuth,
  onUploadReview,
  onDeleteReview,
  onEditReview,
  userStates,
  reviewDatas,
  setReviewDatas,
}) => {
  const newRef = useRef();
  const recommendRef = useRef();
  const [selectedCard, setSelectedCard] = useState(false);
  const [openDeleteConfrim, setOpenDeleteConfirm] = useState(false);
  const [openEditform, setOpenEditForm] = useState(false);
  const [reviewWrite, setReviewWrite] = useState(false);

  const onOpenConfirm = useCallback(() => {
    setOpenDeleteConfirm(true);
  }, []);
  const onCloseConfirm = useCallback(() => {
    setOpenDeleteConfirm(false);
  }, []);
  const onOpenEditForm = useCallback(() => {
    setOpenEditForm(true);
  }, []);
  const onCloseEditForm = useCallback(() => {
    setOpenEditForm(false);
  }, []);
  const onClickReviewWrite = useCallback(() => {
    if (userStates.isLogin) {
      setReviewWrite(true);
    } else {
      onOpenPortalAuth();
    }
  }, [onOpenPortalAuth, userStates]);
  const onCloseReviewWrite = useCallback(() => {
    setReviewWrite(false);
  }, []);
  const onHandleSelected = useCallback((review) => {
    setSelectedCard(review);
  }, []);

  const onHandleDeleteReview = useCallback(
    (id) => {
      onDeleteReview(id);
      setReviewDatas((prev) => {
        const updated = prev.filter((item) => item.id !== id);
        return updated;
      });
    },
    [onDeleteReview, setReviewDatas]
  );
  const onHandleEditReview = useCallback(
    (formData) => {
      // 수정할 리뷰 ID를 받아와야한다.
      onEditReview(selectedCard.id, formData);
    },
    [onEditReview, selectedCard]
  );
  const viewListDate = () => {
    console.log("최신순");
    newRef.current.style = "color:black; opacity:1";
    recommendRef.current.style = "color:gray; opacity: 0.8;";
    setReviewDatas((prev) => {
      const updated = [...prev];
      updated.sort((a, b) => {
        if (a.createdAt > b.createdAt) {
          return 1;
        } else if (a.createdAt < b.createdAt) {
          return -1;
        } else {
          return 0;
        }
      });
      return updated;
    });
  };
  const viewListLikeCount = () => {
    console.log("추천순");
    newRef.current.style = "color:gray; opacity: 0.8;";
    recommendRef.current.style = "color:black; opacity:1";
    setReviewDatas((prev) => {
      const updated = [...prev];
      updated.sort((a, b) => {
        if (a.likeCount < b.likeCount) {
          return 1;
        } else if (a.likeCount > b.likeCount) {
          return -1;
        } else {
          return 0;
        }
      });
      return updated;
    });
  };
  return (
    <div className={styles.reviewList}>
      <div className={styles.reviewList__header}>
        <div className={styles.reviewList__first}>
          <div className={styles.reviewCountBox}>
            <h2>리뷰</h2>
            <h2 className={styles.reviewCount}>{reviewDatas.length}</h2>
          </div>
          <h2 onClick={onClickReviewWrite}>
            <EditTwoTone />
          </h2>
        </div>
        <div className={styles.reviewList__case}>
          <span
            ref={newRef}
            className={styles.reviewList__new}
            onClick={viewListDate}
          >
            최신순
          </span>
          <span
            ref={recommendRef}
            className={styles.reviewList__recommend}
            onClick={viewListLikeCount}
          >
            추천순
          </span>
        </div>
      </div>
      <div className={styles.reviewList__body}>
        {reviewDatas.map((item, index) => (
          <div key={index} className={styles.reviewContainer}>
            <ReviewCard
              review={item}
              isUserReview={item.createdBy === userStates.name}
              onOpenDeleteConfirm={onOpenConfirm}
              onHandleSelected={onHandleSelected}
              onOpenEditForm={onOpenEditForm}
            />
          </div>
        ))}
      </div>
      {openDeleteConfrim && (
        <DeleteConfirm
          onClose={onCloseConfirm}
          onDeleteItem={onHandleDeleteReview}
          deleteItemId={selectedCard.id}
        />
      )}
      <Drawer // 리뷰 작성
        className={styles.reviewFormDrawer}
        visible={reviewWrite}
        placement="bottom"
        height="100vh"
        bodyStyle={{ padding: 0 }}
        onClose={onCloseReviewWrite}
      >
        <ReviewForm
          onUploadReview={onUploadReview}
          onClose={onCloseReviewWrite}
        />
      </Drawer>
      <Drawer // 리뷰 수정
        className={styles.reviewFormDrawer}
        visible={openEditform}
        placement="bottom"
        height="100vh"
        bodyStyle={{ padding: 0 }}
        onClose={onCloseEditForm}
      >
        <ReviewForm
          prevReview={selectedCard}
          onUploadReview={onHandleEditReview}
          onClose={onCloseEditForm}
        />
      </Drawer>
    </div>
  );
};

export default ReviewList;
