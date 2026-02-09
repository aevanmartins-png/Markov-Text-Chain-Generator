# Markov Text Generator

A Java-based predictive text modeling system that uses Markov Chains to analyze input text and generate new sequences. The project builds a directed graph of word transitions, allowing for the generation of the most likely next words or entire sentences based on frequency and probability.

## Features

Efficiently stores word frequencies and adjacency using a VertexNode structure.

Predictive Text Generation: * Most Probable Chain: Generates a sequence by always picking the most frequent following word.

Weighted Random Selection (WRS): Generates more natural-sounding text by picking the next word based on its proportional probability.

Top-K Analysis: Identifies the k most likely words to follow any given "seed" word.

Robust Text Cleaning: Automatically handles case-insensitivity and strips punctuation (preserving underscores).

High Performance: Uses a custom Binary Max Heap for efficient extraction of high-frequency transitions.

## File Description
TextGenerator.java: Handles file I/O, user arguments, and formatting output

MarkovGraph.java: The core data structure that manages the relationships between words.

VertexNode.java : Represents a single word in the graph, tracking its occurrences and a map of words that follow it.

BinaryMaxHeapComp.java:  Implementation of a priority queue used for frequency sorting.
