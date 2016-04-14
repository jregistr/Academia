<!--# Academic-->
<!--#This is a repository where I place a collection of the projects I've worked on for various CS classes.-->
# Our Vision for The Grid Project [Working Title]

--------------------------------------------------------------------------------

## Genre : Action Tactical RPG

The [battle system](#battle-system) consists of two player controlled [hero](#hero-info) characters moving freely on their half of a 10 x 4 grid. Each player is allocated an even 5 x 4 section of the grid. The goal of the game is to reduce the player's hero's HP by applying damage using the best choice of [spells](#spell-cards) at the right time, while also avoiding the other players attacks each round of a [match](#match-info).

### Battle System <a id="battle-system"></a>

The Grid battle system requires the dexterity demanded of an Action RPG, as well as the thought and strategy typical of a TCG. The players are given the opportunity to strategically select the sequence of attacks they would like to use in the next round, without needing to wait their turn to execute them. Rounds, unlike turns in typical TCGs, allow both players to simultaneously execute their spells and move about the playing field. The players must be able to execute their game plan and damage the other player while also preventing their opponent from doing the same.

#### Match <a id="match-info"></a>

A match consists of a series of [rounds](#round-info) that each last up to _[insert time]_, during which each player may cast the [spells](#spell-cards) they have allocated in their [queue](#queue-info). At the end of a round the grid actions are frozen entirely, both player's queues are cleared, and a menu is displayed allowing the player to select the next set of spells to use in their queue for the next round. The match is over when a player's HP reaches 0.

#### Rounds <a id="round-info"></a>

The Round Phase will occur after every [Re-Queue](#queue-phase) phase. The players may move freely about the grid onto tiles that do not have a movement restriction placed on them. Players may activate their queued abilities during this phase only. Any abilities whose [field-effect](#field-effect-info) has a timed duration will remain active for the remainder of their time into the next round. Any field-effect with a round-based duration will be [RFG](#RFG-info)'d at the end of the round in which it was cast.

#### Re-Queue Phase <a id="queue-phase"></a>

At the beginning of this phase if the player does not have 7 in their hand they will draw the difference from their deck if able. The player may place up to 5 cards in their [Spell-Queue](#spell-queue-info), the cards will be mad available to the player in FIFO order during the following round.

#### Spell Cards <a id="spell-cards"></a>

A TCG like unit that will affect the grid field in some way. The effect may be damage dealt to a damage-able unit on the grid, or some lasting field effect that affects multiple entities on the grid.

#### Spell Queue  <a id="spell-queue-info"></a>

The queue of TCG like cards used in combat by the player. Each card can do the following:

- Deal damage to an damage-able entity.
- Grid field effects that last varying amounts of time and may affect multiple entities on the grid.
- Add a Buff or De-buff to a living-entity.

##### Developers

- Jeff Registre
- Rafael O. Torres
- Tom _[enter last name here]_
