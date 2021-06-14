import React, { useCallback, useState } from "react";
import styles from "./cartIcon.module.css";
import { ShoppingCartOutlined } from "@ant-design/icons";
import { Drawer } from "antd";
import ShoppingCart from "../shoppingCart/shoppingCart";
import { onDeleteCart, onDeleteCartAll } from "../../../api/cartAPI";

const CartIcon = ({ userStates, items, onUpdateItems }) => {
  const [cartVisible, setCartVisible] = useState(false);
  const [deleteItemId, setDeleteItemId] = useState(null);

  const setCartVisibleTrue = useCallback(() => {
    // if (userStates.isLogin) {
    setCartVisible(true);
    // } else {
    // setNeedLogin(true);
    // }
  }, [userStates]);
  const setCartVisibleFalse = useCallback(() => {
    setCartVisible(false);
  }, []);

  const onDeleteItem = useCallback(
    async (id) => {
      await onDeleteCart(id);
      onUpdateItems();
    },
    [onUpdateItems]
  );

  const onSetItemForDelete = useCallback((id) => {
    setDeleteItemId(id);
  }, []);

  const resetCartAll = useCallback(async () => {
    await onDeleteCartAll();
    onUpdateItems();
  }, [onUpdateItems]);

  // const updateMemoShoppingItem = useCallback( 추후에 cartIcon에 추가
  //   (id, memo) => {
  //     setShoppingItems((prev) => {
  //       const updated = [...prev];
  //       const forUpdateIndex = updated.findIndex((item) => {
  //         if (item.id === id) return true;
  //       });
  //       updated[forUpdateIndex] = { ...updated[forUpdateIndex], memo: memo };
  //       return updated;
  //     });
  //   },
  //   [setShoppingItems]
  // );

  return (
    <div className={styles.CartIcon}>
      <ShoppingCartOutlined
        className={styles.icon}
        onClick={setCartVisibleTrue}
      />
      <Drawer
        placement="right"
        closable={false}
        visible={cartVisible}
        onClose={setCartVisibleFalse}
        width={window.innerWidth > 768 ? "36vw" : "80vw"}
        bodyStyle={{
          backgroundColor: "#ebecec",
          padding: 0,
        }}
        zIndex={1004}
      >
        <ShoppingCart
          items={items}
          deleteClickedItemId={onSetItemForDelete}
          updateShoppingCart={onUpdateItems}
          // updateMemoShoppingItem={updateMemoShoppingItem}
          resetCartAll={resetCartAll}
          onClose={setCartVisibleFalse}
          deleteItemId={deleteItemId}
          onDeleteItem={onDeleteItem}
        />
      </Drawer>
    </div>
  );
};

export default CartIcon;
