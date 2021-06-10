import React, { useCallback, useState } from "react";
import styles from "./cartIcon.module.css";
import { ShoppingCartOutlined } from "@ant-design/icons";
import { Drawer } from "antd";
import ShoppingCart from "../shoppingCart/shoppingCart";
import { onDeleteCartAll, onDeleteCartItem } from "../../../api/cartAPI";

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
    (id) => {
      onUpdateItems();
      onDeleteCartItem(id);
    },
    [onUpdateItems]
  );

  const onSetItemForDelete = useCallback((id) => {
    setDeleteItemId(id);
  }, []);

  const resetCartAll = useCallback(() => {
    onUpdateItems();
    onDeleteCartAll();
  }, [onUpdateItems]);

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
