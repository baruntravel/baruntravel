import Modal from "antd/lib/modal/Modal";
import React, { useCallback, useRef, useState } from "react";
import styles from "./portalCartBody.module.css";
import "antd/dist/antd.css";
import { v4 as uuidv4 } from "uuid";

import { useRecoilState } from "recoil";
import { myRouteCart } from "../../recoil/routeAtom";
import RouteNameBox from "./routeNameBox/routeNameBox";

const PortalCartBody = ({ place, handleCartPortalClose }) => {
  const [modalVisible, setModalVisible] = useState(false);
  const [myRouteList, setMyRouteList] = useRecoilState(myRouteCart);
  const inputRef = useRef();
  const handleOpen = () => {
    setModalVisible(true);
  };
  const handleClose = () => {
    setModalVisible(false);
  };
  const handleOk = (event) => {
    event.preventDefault();
    setMyRouteList((prev) => {
      return {
        ...prev,
        [uuidv4()]: {
          routeName: inputRef.current.value,
          centerLocation: [], // centerLocation은 어떤기준으로 정해줄지 고민해볼 필요가 있다.
          places: [],
          wishCount: 0,
        },
      };
    });
    inputRef.current.value = "";
    handleClose();
  };
  const addPlace = (key) => {
    const newPlaceObj = {
      id: place.id,
      category: place.category_group_code,
      placeName: place.place_name,
      placeUrl: place.place_url,
      addressName: place.road_address_name || place.address_name,
      x: place.x,
      y: place.y,
    };
    setMyRouteList((prev) => {
      const updated = { ...prev };
      updated[key] = {
        routeName: updated[key].routeName,
        centerLocation: updated[key].centerLocation,
        places: [...updated[key].places, newPlaceObj],
      };
      return updated;
    });
    handleCartPortalClose();
  };
  return (
    <div className={styles.PortalCartBody}>
      <ul className={styles.routeList}>
        {myRouteList &&
          Object.keys(myRouteList).map((objKey, index) => (
            <RouteNameBox
              key={index}
              item={myRouteList[objKey]}
              objKey={objKey}
              addPlace={addPlace}
            />
          ))}
      </ul>
      <div className={styles.btnBox}>
        <button className={styles.addBtn} onClick={handleOpen}>
          새로 추가
        </button>
        <button className={styles.closeBtn} onClick={handleCartPortalClose}>
          Close
        </button>
      </div>
      <Modal
        title="새로운 여행 카트를 추가해요"
        style={{ marginTop: "10%" }}
        visible={modalVisible}
        onOk={handleOk}
        onCancel={handleClose}
        zIndex="1001"
      >
        <form className={styles.form} onSubmit={handleOk}>
          <input
            ref={inputRef}
            type="text"
            className={styles.input}
            placeholder="여행의 주제가 뭘까요?"
            autoFocus
          />
        </form>
      </Modal>
    </div>
  );
};

export default PortalCartBody;
