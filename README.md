# All About Reactions with the Stream Chat Android SDK



## Introduction

In modern chat apps, you need something to make the messaging platforms a bit lively and increase user engagement. Reactions have emerged as a top feature to help in this.

Stream provides a nice SDK for you to add reactions in a matter of minutes in your application. 

In this tutorial you're going to learn how to:

- Add reactions to a message
- Deleting a reaction to a message
- Cumulative Reactions
- Paginating Reactions

**Note:** This tutorial assumes you already know the basic knowledge on the Stream API's. To quickly get started checkout the [Android Chat Messaging Tutorial](https://getstream.io/tutorials/android-chat/#kotlin)

## Adding a Reaction

![reactions](/Users/harun/AndroidStudioProjects/StreamReactions/images/reactions.png)

Reactions are mostly found on most social media apps and there's a number of them which are famously known:

- Like
- Favourite/Love
- Sad/Angry
- Clap

They're mostly used to represent common quick reactions that users can have after seeing a post.  Using the Stream SDK, you can easily add reactions to your messages in a very easy way.

You'll be seeing how to add a reaction. First, you have to create a <code>Reaction</code> object which has properties of your reaction.

```Kotlin
val reaction = Reaction(
    messageId = sentMessage.id,
    type = reactionModel.name,
    score = reactionModel.score,
    extraData = mutableMapOf("customField" to 1),
)
```

In the code above:

- You're adding the <code>messageId</code> for the message that you want to react to.
- You specify the type of the reaction.
- Here, you're also adding the score for the reaction
- Lastly, you add any additional extra information.

Now, you have your reaction object ready, next you'll be seeing how to send that reaction over to the Stream Client.

To send a message reaction, you need a <code>ChannelClient</code>. A <code>ChannelClient</code> enables you to:

- Create Channels
- Add users to channels
- Add messages to channels
- React to messages in out client.

On the sample app, the client is declared at the top of the file as:

```Kotlin
private lateinit var channelClient: ChannelClient
```

It's initialised in your <code>onCreate</code> method as follows:

```kotlin
channelClient = client.channel(channelType = "messaging", channelId = "general")
```

It needs a <code>ChatClient</code> which is responsible for initializing the Stream Chat Client in your application. Now that you have everything set, to send your reaction object, you need the following code:

```Kotlin
channelClient.sendReaction(reaction).enqueue { result ->
    if (result.isSuccess) {
        val sentReaction = result.data()
        hideReactions(sentReaction)
    } else {
        showSnackBar("Adding like reaction Failed")
    }
}
```

Here, you send your reaction object to Stream and wait for the callback response which returns a success or a failure. Depending on the result, you handle the states according to your app's needs. In this scenarion you can see it hides the reaction view and shows the reaction you've tapped with the score along side it as in the image below:

<img src="/Users/harun/AndroidStudioProjects/StreamReactions/images/reactions_count.png" alt="reactions_count" style="zoom:40%;" />

Congratulations! You've just learned how to add your first message reaction. With the Stream SDK it's very easy and seemless way to do that. Next, you'll be seeing how to remove the reaction.

## Removing a Reaction

To remove a reaction, you only need the <code>messageId</code> and <code>reactionType</code>. The code to remove is as follows:

```Kotlin
channelClient.deleteReaction(
    messageId = reaction.messageId,
    reactionType = reaction.type,
).enqueue { result ->
    if (result.isSuccess) {
        hideReactionCount()
    } else {
        showSnackBar("Delete Failed")
    }
}
```

This method is similar to the one for adding reactions only difference is that you're calling the <code>deleteReaction</code> method to delete the reaction. You're also passing the <code>messageId</code> and <code>reactionType</code> for the reaction that you want to remove. You're also handling the success and error states.

## Cumulative Reactions

Another famous reaction is the clap reaction or enabling a user to react more than once. This is mostly useful in blogs and articles. The Reactions API allows this functionality out of the box. This is supported by adding the <code>score</code> in your reaction model as shown below:

```Kotlin
ReactionModel("clap",R.drawable.ic_clapping, 25)
```

This is a custom data class for showing the available reactions in our application. It has the three properties as follow:

```Kotlin
data class ReactionModel (
    val name: String,
    val image: Int,
    val score: Int
    )
```

And to add your reaction score to Stream SDK here's is what you do:

```Kotlin
val reaction = Reaction(
    messageId = sentMessage.id,
    type = reactionModel.name,
    score = reactionModel.score,
    extraData = mutableMapOf("customField" to 1),
)
```

Here you specify the <code>score</code> for your reaction. And now you can send the reaction in a similar manner as follows:

```kotlin
channelClient.sendReaction(reaction).enqueue { result->
    if (result.isSuccess) {
        val sentReaction = result.data()
        hideReactions(sentReaction)
    } else {
        showSnackBar("Adding clap reaction Failed")
    }
}
```

The code is similar to the one for adding a like reaction that you saw above.

## Paginating Reactions

## Customizing the Reactions UI

## Adding your Custom Styling to Reactions





## Conclusion

You've learned how to add, remove, paginate and add cumulative reactions in this tutorial. 

The Stream SDK also provides out of the box UI Features for reactions which you can explore also incase you need to quickly add reactions in your app without having to worry about the UI and UX part. 

Also as you've seen in this tutorial, it's easy to intergrate reactions to your existing app and use the Reactions API to enrich your app.

