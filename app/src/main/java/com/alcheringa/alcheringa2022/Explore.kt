package com.alcheringa.alcheringa2022

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun exploreCard (){
    Card(
        elevation = 6.dp,
        modifier = Modifier
            .size(width = 100.dp, height = 100.dp)
    ) {
           Image(painter=painterResource(id = R.drawable.frame_15202_merch_), contentDescription = "")

    }
}
@Preview(showBackground = true)
@Composable
fun lazyRows (){
       LazyRow( modifier = Modifier.fillMaxWidth(),
           horizontalArrangement = Arrangement.spacedBy(8.dp),
           contentPadding = PaddingValues(horizontal = 20.dp)
       ){
           // item 1 merch
           item {
               Card(
                   elevation = 6.dp,
                   modifier = Modifier
                       .size(width = 100.dp, height = 100.dp)
               ) {
                   Image(painter=painterResource(id = R.drawable.frame_15202_merch_), contentDescription = "")

               }
           }
            // item 2 event
           item {
               Card(
                   elevation = 6.dp,
                   modifier = Modifier
                       .size(width = 100.dp, height = 100.dp)
               ) {
                   Image(painter=painterResource(id = R.drawable.frame_15207_events), contentDescription = "")

               }
           }
           // item 3 competitions
           item {
               Card(
                   elevation = 6.dp,
                   modifier = Modifier
                       .size(width = 100.dp, height = 100.dp)
               ) {
                   Image(painter=painterResource(id = R.drawable.frame_15209_comp), contentDescription = "")

               }
           }
           // item 4

           item {
               Card(
                   elevation = 6.dp,
                   modifier = Modifier
                       .size(width = 100.dp, height = 100.dp)
               ) {
                   Image(painter=painterResource(id = R.drawable.frame_15202_merch_), contentDescription = "")

               }
           }
       }
}