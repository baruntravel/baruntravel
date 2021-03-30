import { useState, useEffect } from "react";
import styles from "./cartModal.module.css";
import "antd/dist/antd.css";
import { Modal } from "antd";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faShoppingCart } from "@fortawesome/free-solid-svg-icons";

import { useRecoilState } from "recoil";
import { myRouteCart } from "../../../recoil/routeAtom";

const CartModal = ({ isRoute }) => {
  //TODO : 루트 추가 기능 구현

  const [isModalVisible, setIsModalVisible] = useState(false);
  const [myRouteList, setMyRouteList] = useRecoilState(myRouteCart);

  useEffect(() => {
    // console.log(myRouteList);
  }, []);

  const showModal = (e) => {
    setIsModalVisible(true);
    e.stopPropagation();
    console.log(e.target);
  };

  const handleOk = () => {
    setIsModalVisible(false);
  };

  const handleCancel = () => {
    setIsModalVisible(false);
  };

  return (
    <>
      <button className={styles.cartButton} onClick={showModal}>
        <FontAwesomeIcon icon={faShoppingCart} size={"sm"} />
      </button>
      <Modal
        title="Basic Modal"
        visible={isModalVisible}
        onOk={handleOk}
        onCancel={handleCancel}
      >
        {isRoute ? (
          //Route의 Cart 버튼 누를시
          <>
            <h1 className={styles.title}>나의 루트에 담기</h1>
            {Object.keys(myRouteList).map((item) => {
              console.log(item);
              return <li className={styles.routeName}>{item}</li>;
            })}
            <input
              className={styles.newRoute}
              placeholder="루트 이름을 입력 해주세요"
            ></input>
          </>
        ) : (
          //Place의 Cart 버튼 누를시
          <>
            <h1 className={styles.title}>기존 루트에 장소 추가하기</h1>
            {Object.keys(myRouteList).map((item) => {
              console.log(item);
              return <li className={styles.routeName}>{item}</li>;
            })}
          </>
        )}
      </Modal>
    </>
  );
};

export default CartModal;
