package com.scaler.splitwise.controllers;

import com.scaler.splitwise.dtos.SettleUpGroupRequestDto;
import com.scaler.splitwise.dtos.SettleUpGroupResponseDto;
import com.scaler.splitwise.dtos.SettleUpUserRequestDto;
import com.scaler.splitwise.dtos.SettleUpUserResponseDto;
import org.springframework.stereotype.Controller;

@Controller
public class SettleUpController {

   /*
   Returns list of transactions
   */
   public SettleUpUserResponseDto settleUpUser(SettleUpUserRequestDto settleUpUserRequestDto) {
     return null;
   }

   public SettleUpGroupResponseDto settleUpGroup(SettleUpGroupRequestDto settleUpGroupRequestDto) {
       return null;
   }
}
