import { useState, useEffect } from "react";
import styles from "./cartModal.module.css";
import "antd/dist/antd.css";
import { Modal } from "antd";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faShoppingCart } from "@fortawesome/free-solid-svg-icons";

import { useRecoilState } from "recoil";
import { myRouteCart } from "../../../recoil/routeAtom";

const CartModal = ({ isRoute }) => {
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
            <h1 className={styles.title}>루트 추가하기</h1>
            {Object.keys(myRouteList).map((item) => {
              console.log(item);
              return <li className={styles.routeName}>{item}</li>;
            })}
          </>
        ) : (
          //Place의 Cart 버튼 누를시
          <>
            <h1 className={styles.title}>장소 추가하기</h1>
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
